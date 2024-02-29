package exercise.mapper;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.model.Author;
import org.mapstruct.*;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class AuthorMapper {

    // BEGIN
    //@Mapping(source = "author.id", target = "author")
    public abstract AuthorDTO map(Author author);

    //@Mapping(source = "author", target = "authorId")
    public abstract Author map(AuthorCreateDTO authorCreateDTO);
    // END

    public abstract void update(AuthorUpdateDTO dto, @MappingTarget Author model);
}
