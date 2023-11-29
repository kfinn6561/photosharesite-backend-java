package com.photosharesite.backend.endpoints.getfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.photosharesite.backend.db.selectfiles.SelectFilesAccess;
import com.photosharesite.backend.db.selectfiles.SelectFilesResponse;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class GetFilesResourceTest {

  @Test
  public void testGetAllFiles() {
    String testIPAddress = "127.0.0.1";

    // Create a mock DAO instance
    SelectFilesAccess mockDAO = mock(SelectFilesAccess.class);

    // Create a mock SelectFilesResponse
    SelectFilesResponse modifyableResponse =
        new SelectFilesResponse(1, "test1", "http://example.com", testIPAddress);
    SelectFilesResponse nonModifyableResponse =
        new SelectFilesResponse(2, "test2", "http://example.co.uk", "123.0.0.0");

    List<SelectFilesResponse> mockResponses =
        Arrays.asList(modifyableResponse, nonModifyableResponse);

    // Configure mock DAO to return the mock responses
    when(mockDAO.SelectFiles()).thenReturn(mockResponses);

    // Create an instance of GetFilesResource with the mock DAO
    GetFilesResource resource = new GetFilesResource(mockDAO);

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
