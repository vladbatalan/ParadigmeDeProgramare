package org.example

import khttp.responses.Response

class CleanGetRequest(private var getReq:GetRequest) : HTTPGet{
    private val parentalControlDisallow : List<String> = listOf(
        "KOTLIN",
        "PARADIGME"
    )
    override fun getResponse(): Response? {
        //here we clean the getReq response
        val response = getReq.getResponse()

        //create regex for completely eliminate each word from parental control
        var deleteRegexString = ""
        parentalControlDisallow.forEachIndexed { index, str ->
            deleteRegexString += "(\\b(${str.toUpperCase()})\\b)"
            if(index != parentalControlDisallow.size-1)
                deleteRegexString += "|"
        }

        val deleteRegex = deleteRegexString.toRegex()
        val responseOriginalContent = response?.text?.toUpperCase()
        val regexSolutionExists = responseOriginalContent?.let { deleteRegex.find(it) }

        if (regexSolutionExists != null) {
            return null;
        }

        //send the cleaned response
        return response
    }


}
