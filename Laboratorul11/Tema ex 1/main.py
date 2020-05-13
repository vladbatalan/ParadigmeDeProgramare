import asyncio.futures
import queue

my_queue = queue.Queue()


async def sum_function(future):
    if ~my_queue.empty():

        top_element = my_queue.get()

        sum = 0
        for i in range(1, top_element + 1):
            sum += i

        future.set_result([top_element, sum])
    else:
        future.set_result([-1, -1])
        print("There are no elements left in queue!")


def show_result(future):
    arry = future.result()

    number = arry[0]
    result = arry[1]

    print("Future calculated for [{}]: obtained {}\n".format(number, result))


if __name__ == "__main__":

    # adding numbers in queue
    for i in range(4, 11):
        my_queue.put(i)

    # iniating the Tasks
    future_array = [asyncio.Future() for i in range(4)]
    tasks = [sum_function(future) for future in future_array]

    for future in future_array:
        future.add_done_callback(show_result)

    # setting my loop
    loop = asyncio.get_event_loop()
    loop.run_until_complete(asyncio.wait(tasks))


    loop.close()