package ru.bainc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookSearchDto {
      private Long idTag;
      private String partTitle;
      private String partSurname;
      private List<Long> tagsListId;
}
