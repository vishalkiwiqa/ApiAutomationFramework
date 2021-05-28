package com.MBS.Init;

public interface HTTPStatusCode {

    int    HTTP_OK = 200,
            HTTP_Created = 201,
            HTTP_Accepted = 202,
            HTTP_No_Content = 204,
            HTTP_Temporary_Redirect = 307,
            HTTP_Not_Modified = 304,
            HTTP_Bad_Request = 400,
            HTTP_Forbidden = 403,
            HTTP_Unauthorized = 401,
            HTTP_Not_Fount = 404,
            HTTP_Request_Timeout = 408,
            HTTP_Payment_required = 402,
            HTTP_Conflict = 409,
            HTTP_Internal_Server_Error = 500,
            HTTP_Bad_Gateway = 502,
            HTTP_Service_Unavailable = 503,
            HTTP_Gateway_Timeout = 504;

}
