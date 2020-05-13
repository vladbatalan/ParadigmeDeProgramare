import subprocess
import threading
import queue
from concurrent.futures.thread import ThreadPoolExecutor

q = queue.Queue()
elem = []
conditie = threading.Condition()
no_of_tasks = 0


def process_command(comm_str, previous_stdout=None):
    str(comm_str).strip()
    print("\tExecute command ", comm_str)

    # based on the last component, we create the comunication
    if previous_stdout is None:
        process = subprocess.Popen(comm_str, stdout=subprocess.PIPE, shell=True, )
    else:
        process = subprocess.Popen(comm_str, stdin=previous_stdout, stdout=subprocess.PIPE, shell=True, )

    output, error = process.communicate()
    print("-> output is\n{}\n".format(output))

    return process.stdout


def command_worker(command, number):
    # the number gets the order of the triggering
    # the first one
    global elem
    conditie.acquire()
    print("Thread[{}] -> {}".format(number, command))
    if number == 0:
        process = subprocess.Popen(command, stdout=subprocess.PIPE, shell=True, )
        q.put([process, number + 1])
        elem.append(number + 1)
        print("current queue: ", elem)

        print("Process {} status: done!".format(number))
        conditie.notify()
        conditie.release()
        return

    # we need to wait for the good element from the queue
    my_pair = []
    got_result = False
    while not got_result:
        got_result = False
        while q.empty():
            print("Process {} status: waiting for queue!".format(number))
            print("current queue: ", elem)
            conditie.wait()
        my_pair = q.get()
        elem = []
        print("Process {} status: extracted one element!".format(number))
        print("current queue: ", elem)
        if my_pair[1] != number:
            q.put(my_pair)
            elem.append(my_pair[1])
            print("Process {} status: waiting after extracting the wrong number!".format(number))
            print("current queue: ", elem)
            #conditie.wait()
        else:
            got_result = True

    process = subprocess.Popen(command, stdin=my_pair[0].stdout, stdout=subprocess.PIPE, shell=True, )
    q.put([process, number + 1])
    elem.append(number + 1)

    print("current queue: ", elem)
    print("Process {} status: done!".format(number))
    conditie.notify()
    conditie.release()


if __name__ == "__main__":
    pipe_command = input("Insert the pipe bash command here:\n")

    commands = pipe_command.split("|")
    my_tasks = []

    for item in range(0, commands.__len__()):
        my_tasks.append(threading.Thread(target=command_worker, args=(commands[item], item)))

    for task in my_tasks:
        task.start()
    for task in my_tasks:
        task.join()

    if ~q.empty():
        result = q.get()
        output, error = result[0].communicate()
        print(output)

    else:
        print("Looks like we got a problem...")
