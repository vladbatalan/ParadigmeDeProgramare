package org.example

import khttp.responses.Response

interface HTTPGet {
    fun getResponse() : Response?
}