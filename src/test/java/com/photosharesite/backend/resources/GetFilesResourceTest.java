package com.photosharesite.backend.resources;

import com.photosharesite.backend.api.GetFilesRequest;
import com.photosharesite.backend.api.GetFilesResponse;
import com.photosharesite.backend.db.SelectFilesResponse;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GetFilesResourceTest {

    @Test
    public void testGetAllFiles() {
        // Create a mock Jdbi instance
        Jdbi mockJdbi = mock(Jdbi.class);

        // Create a test GetFilesRequest
        GetFilesRequest testRequest = new GetFilesRequest();

        // Create a mock SelectFilesResponse
        SelectFilesResponse mockResponse = mock(SelectFilesResponse.class);
        when(mockResponse.getFileID()).thenReturn(1);
        when(mockResponse.getURL()).thenReturn("http://example.com");
        when(mockResponse.getFileName()).thenReturn("test.jpg");
        when(mockResponse.getIPAddress()).thenReturn("127.0.0.1");

        // Create a mock Handle and configure it to return the mock response
        Handle mockHandle = mock(Handle.class);
        when(mockHandle.createQuery(anyString())).thenReturn(mock(Query.class));
        when(mockHandle.mapToBean(eq(SelectFilesResponse.class))).thenReturn(mockResponse);

        // Configure the mock Jdbi to return the mock Handle
        when(mockJdbi.withHandle(any())).thenAnswer(invocation -> {
            Function<Handle, List<GetFilesResponse>> function = invocation.getArgument(0);
            return function.apply(mockHandle);
        });

        // Create an instance of GetFilesResource with the mock Jdbi
        GetFilesResource resource = new GetFilesResource(mockJdbi);

        // Call the getAllFiles method
        List<GetFilesResponse> result = resource.getAllFiles(testRequest);

        // Verify the result
        assertEquals(1, result.size());
        GetFilesResponse response = result.get(0);
        assertEquals(1, response.getFileID());
        assertEquals("http://example.com", response.getURL());
        assertEquals("test.jpg", response.getFileName());
        assertEquals(true, response.getIsIPAddressMatch());
    }
}
