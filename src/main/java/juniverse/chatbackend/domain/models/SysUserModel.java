package juniverse.chatbackend.domain.models;

import juniverse.chatbackend.domain.enums.Major;
import juniverse.chatbackend.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserModel {


    private String firstName;

    private String password;

    private String username;

    private Major major;

    private String lastName;

    private String email;

    private UserRole role;

    private String profilePicturePath;

    private String coverPicturePath;

    private String bio;
}
