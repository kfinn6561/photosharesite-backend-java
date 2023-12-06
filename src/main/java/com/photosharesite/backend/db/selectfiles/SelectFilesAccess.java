package com.photosharesite.backend.db.selectfiles;

import com.google.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import org.jdbi.v3.core.Jdbi;

public class SelectFilesAccess {
  public static final String SELECT_FILES_PROC_NAME = "SelectFiles";
  private final Jdbi jdbi;

  @Inject
  public SelectFilesAccess(Jdbi jdbi) {
    this.jdbi = jdbi;
  }

  public List<SelectFilesResponse> SelectFiles() {
    return jdbi.withHandle(
        handle ->
            handle
                .createQuery("CALL " + SELECT_FILES_PROC_NAME + "()")
                .mapToBean(SelectFilesResponse.class)
                .collect(Collectors.toList()));
  }
}
