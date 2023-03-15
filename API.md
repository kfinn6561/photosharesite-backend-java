# API endpoints reference

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