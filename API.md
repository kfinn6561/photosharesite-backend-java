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
| UserId    | int  |       | 1234    |


## Delete File
Removes a file from the database and also deletes it from AWS.

### Endpoint
<photosharesite-backend>/files/<file-id>

### HTTP method
DELETE

### Request
| Parameter | Type | Notes                                                 | Example |
|-----------|------|-------------------------------------------------------|---------|
| UserId    | int  | used to confirm we have the right to delete this file | 1234    |