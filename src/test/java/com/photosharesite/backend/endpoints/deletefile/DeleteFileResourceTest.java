package com.photosharesite.backend.endpoints.deletefile;

import com.photosharesite.backend.db.deletefile.DeleteFileAccess;
import com.photosharesite.backend.db.getfiledetails.GetFileDetailsAccess;
import com.photosharesite.backend.db.getfiledetails.GetFileDetailsResponse;
import com.photosharesite.backend.db.insertorselectuser.InsertOrSelectUserAccess;
import com.photosharesite.backend.exceptions.AccessDeniedException;
import com.photosharesite.backend.exceptions.EntityNotFoundException;
import com.photosharesite.backend.filemanipulation.FileDeleter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeleteFileResourceTest {
    private static final String testIPAddress="127.0.0.0";
    private static final int testUserID=123;
    private static final int testFileID=321;
    private static final String testFileName="example.jpg";
    private static final DeleteFileRequest request=new DeleteFileRequest(testFileID,testIPAddress);
    @Mock InsertOrSelectUserAccess selectUserDAO;
    @Mock GetFileDetailsAccess fileDetailsDAO;
    @Mock DeleteFileAccess deleteFileDAO;
    @Mock
    FileDeleter fileDeleter;

    private  DeleteFileResource deleteFileResource;

    @BeforeEach
    public void setup(){
        when(selectUserDAO.InsertOrSelectUser(testIPAddress)).thenReturn(testUserID);
        this.deleteFileResource=new DeleteFileResource(deleteFileDAO,fileDetailsDAO,selectUserDAO,fileDeleter);
    }

    @Test
    public void deleteFile_HappyPath() throws AccessDeniedException, EntityNotFoundException {
        // Given: the file belongs to the user
        when(fileDetailsDAO.GetFileDetails(testFileID)).thenReturn(Optional.of(fileBelongingToUser()));

        // When: we attempt to delete the file
        deleteFileResource.deleteFile(request);

        // Then: the file is deleted
        verify(deleteFileDAO).DeleteFile(testFileID);
        verify(fileDeleter).DeleteFile(testFileName);
    }

    @Test
    public void deleteFile_AccessDenied() {
        // Given: the file does not belong to the user
        when(fileDetailsDAO.GetFileDetails(testFileID)).thenReturn(Optional.of(fileNotBelongingToUser()));

        // When: we attempt to delete the file
        Assertions.assertThrows(AccessDeniedException.class,()-> deleteFileResource.deleteFile(request));

        // Then: an AccessDeniedException is thrown and the file is not deleted
        verify(deleteFileDAO, never()).DeleteFile(testFileID);
        verify(fileDeleter, never()).DeleteFile(testFileName);
    }

    private GetFileDetailsResponse fileBelongingToUser(){
        return new GetFileDetailsResponse("http.example.com",testFileName,testUserID);
    }

    private GetFileDetailsResponse fileNotBelongingToUser(){
        return new GetFileDetailsResponse("http.example.com",testFileName,73283927);
    }
}
