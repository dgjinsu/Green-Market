package user_service.security.principal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import user_service.entity.Role;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class UserDto {

    private Long id;
    private Role role;
}
