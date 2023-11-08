package com.photosharesite.backend.endpoints.deletefile;

import com.photosharesite.backend.db.deletefile.DeleteFileAccess;
import com.photosharesite.backend.db.getfiledetails.GetFileDetailsAccess;
import com.photosharesite.backend.db.insertorselectuser.InsertOrSelectUserAccess;
import com.photosharesite.backend.filemanipulation.FileDeleter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeleteFileResourceTest {
    private static final int testUserID=123;
    private static final int testFileID=321;
    @Mock InsertOrSelectUserAccess selectUserDAO;
    @Mock GetFileDetailsAccess fileDetailsDAO;
    @Mock DeleteFileAccess deleteFileDAO;
    @Mock
    FileDeleter fileDeleter;

    private final DeleteFileResource deleteFileResource = new DeleteFileResource(deleteFileDAO,fileDetailsDAO,selectUserDAO,fileDeleter);

    @Test
    public void deleteFile_HappyPath(){
        when()
    }
}
