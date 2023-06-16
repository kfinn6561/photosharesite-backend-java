package com.photosharesite.backend.resources;

import com.photosharesite.backend.api.GetFilesRequest;
import com.photosharesite.backend.api.GetFilesResponse;
import com.photosharesite.backend.db.SelectFilesResponse;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.HandleCallback;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.result.ResultIterable;
import org.jdbi.v3.core.statement.Query;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetFilesResourceTest {

    @Test
    public void testGetAllFiles() {
        String testIPAddress = "127.0.0.1";

        // Create a mock Jdbi instance
        Jdbi mockJdbi = mock(Jdbi.class);

        // Create a mock SelectFilesResponse
        SelectFilesResponse modifyableResponse = new SelectFilesResponse(1,"test1","http://example.com",testIPAddress);
        SelectFilesResponse nonModifyableResponse = new SelectFilesResponse(2,"test2","http://example.co.uk","123.0.0.0");

        List<SelectFilesResponse> mockResponses = Arrays.asList(
                modifyableResponse,
                nonModifyableResponse
        );

        // Create a mock Handle and configure it to return the mock responses
        Handle mockHandle = mock(Handle.class);
        Query mockQuery = mock(Query.class);
        ResultIterable<SelectFilesResponse> mockResultIterable = mock(ResultIterable.class);
        when(mockHandle.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.mapToBean(eq(SelectFilesResponse.class))).thenReturn(mockResultIterable);
        when(mockResultIterable.stream()).thenReturn(mockResponses.stream());

        // Configure the mock Jdbi to return the mock Handle
        when(mockJdbi.withHandle(any()))
                .thenAnswer(invocation -> {
                    // Get the callback from the invocation
                    HandleCallback callback = invocation.getArgument(0);
                    // Invoke the callback with the mock Handle object
                    return callback.withHandle(mockHandle);
                });

        // Create an instance of GetFilesResource with the mock Jdbi
        GetFilesResource resource = new GetFilesResource(mockJdbi);

        // Create a test GetFilesRequest
        GetFilesRequest testRequest = new GetFilesRequest(testIPAddress);

        // Call the getAllFiles method
        List<GetFilesResponse> result = resource.getAllFiles(testRequest);

        // Verify the result
        assertEquals(2, result.size());

        GetFilesResponse response1 = result.get(0);
        assertEquals(1, response1.getFileID());
        assertEquals("http://example.com", response1.getDownloadURL());
        assertEquals("test1", response1.getFileName());
        assertTrue(response1.isIsModifyable());

        GetFilesResponse response2 = result.get(1);
        assertEquals(2, response2.getFileID());
        assertEquals("http://example.co.uk", response2.getDownloadURL());
        assertEquals("test2", response2.getFileName());
        assertFalse(response2.isIsModifyable());
    }
}
