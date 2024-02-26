package exercise.mapper;

import exercise.dto.CategoryCreateDTO;
import exercise.dto.CategoryDTO;
import exercise.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

// BEGIN
@Mapper(
        uses = { JsonNullableMapper.class, ReferenceMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
//    public abstract User map(UserCreateDTO model);
//
//    public abstract User map(UserUpdateDTO model);
//
//    @Mapping(target = "username", source = "email")
//    @Mapping(target = "password", ignore = true)
//    public abstract UserDTO map(User model);
//
//    public abstract void update(UserUpdateDTO update, @MappingTarget User destination);

public abstract class CategoryMapper {
    //public abstract void update(CarUpdateDTO dto, @MappingTarget Car model);

    public abstract Category map(CategoryCreateDTO carDTO);
    public abstract CategoryDTO map(Category model);

}

// END
