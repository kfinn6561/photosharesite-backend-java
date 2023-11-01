# API endpoints reference

## Get User ID
Retrieves the UserID from a given IP address. If no user exists in the DB, a new one is created

### Endpoint
<photosharesite-backend>/user/lookup

### HTTP method
POST

### Request
| Parameter | Type   | Notes | Example     |
|-----------|--------|-------|-------------|
| IPAddress | string |       | "127.1.1.0" |

### Response
| Parameter | Type | Notes | Example |
|-----------|------|-------|---------|
| userID    | int  |       | 1234    |

## Get All Files
Returns details of all files on the server, determines which files are modifyable based on the supplied IP address.

### Endpoint
<photosharesite-backend>/files/all

### HTTP method
POST

### Request
| Parameter | Type   | Notes | Example     |
|-----------|--------|-------|-------------|
| IPAddress | string |       | "127.1.1.0" |

### Response
List of

| Parameter    | Type   | Notes                                                             | Example                    |
|--------------|--------|-------------------------------------------------------------------|----------------------------|
| fileID       | int    |                                                                   | 1234                       |
| fileName     | string |                                                                   | "me at the beach"          |
| downloadURL  | string | url to set as src on the frontend                                 | "s3://photosharesite/1234" |
| isModifyable | bool   | determined by whether the supplied IP Address is was the uploader | true                       |

## Upload File
Uploads a file to S3 and also adds it to the database

### Endpoint
<photosharesite-backend>/files/upload?userid=<userid>

### HTTP method
POST

### Request
| Parameter   | Type   | Notes           | Example           |
|-------------|--------|-----------------|-------------------|
| UserId      | int    | query parameter | 1234              |
| fileContent | binary |                 | <file>            |

### Response
none

## Delete File
Removes a file from the database and also deletes it from AWS.

### Endpoint
<photosharesite-backend>/files/delete

### HTTP method
POST

### Request
| Parameter | Type | Notes                                                 | Example |
|-----------|------|-------------------------------------------------------|---------|
| FileID    | int  |                                                       | 4321    |
| UserId    | int  | used to confirm we have the right to delete this file | 1234    |

### Response
none