package myprojects.foodshop.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import myprojects.foodshop.exceptions.NameMissingException;
import myprojects.foodshop.exceptions.NotMatchingPasswords;
import myprojects.foodshop.exceptions.PasswordMissingException;
import myprojects.foodshop.exceptions.ShortPasswordException;
import myprojects.foodshop.models.Role;
import myprojects.foodshop.models.User;
import myprojects.foodshop.models.dtos.userDtos.CreateUserDto;
import myprojects.foodshop.models.dtos.userDtos.UserResponseDto;
import myprojects.foodshop.repositories.RoleRepository;
import myprojects.foodshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Transactional // I need everything in this class to be transactional
@Slf4j // for log
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    // those are repositories
    // those are communicating with JPA directly, which in turn is doing a lot of things in the backend
    // to create those queries for us and inquiry the database for us
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String usersRole = "ROLE_USER";

    @Autowired
    // we need the constructor because we have all these fields final, and we need to inject them in this class
    // that's how we're going to do our dependency injection
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the database",user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserResponseDto registerUser(CreateUserDto dto){
        /*if (dto.username.isEmpty()) {
            throw new UsernameMissingException();
        }*/
        if (dto.password.isEmpty()) {
            throw new PasswordMissingException();
        }
        if (dto.name.isEmpty()) {
            throw new NameMissingException();
        }

        if (!dto.password.equals(dto.passwordAgain)){
            throw new NotMatchingPasswords();
        }

        /*boolean usernameExist = userRepository.findByUsername(dto.username).isPresent();
        if (usernameExist) {
            throw new UsernameAlreadyTakenException();
        }

        if (dto.username.length() < 4) {
            throw new ShortUsernameException();
        }*/

        if (dto.password.length() < 8) {
            throw new ShortPasswordException();
        }

        /*EmailValidator.validate(dto.email);
        String secret = totpManager.generateSecret();
        Long tokenExpiration =
                Long.parseLong(
                        environment.getProperty(
                                "config.security.token.expiration.email_verification", DEFAULT_TOKEN_EXPIRATION));
        User user = new User(dto, tokenExpiration, secret);
        userRepository.save(user);
        UserResponseDto userResponseDto =
                new UserResponseDto(user, totpManager.getUriForImage(user.getSecret()));

        // TODO
        // send the verification email

        boolean userCreated = userRepository.findByUsername(dto.username).isPresent();
        if (!userCreated) {
            throw new UnexpectedErrorException();
        }*/
        User user = new User(dto);
        Role role = roleRepository.findRoleByName(usersRole);
        user.getRoles().add(role);

        log.info("Saving new user {} to the database",user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return new UserResponseDto(user.getName());
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("Adding role {} to user {}",roleName,email);
        User user = userRepository.findByEmail(email).get();
        Role role = roleRepository.findRoleByName(roleName);
        user.getRoles().add(role);

        // because I used @Transactional -> it's just going to go ahead and save everything in a database
        // -> I don't have to call the user repository and then save anything again
    }

    @Override
    public User getUser(String email) {
        log.info("Fetching user {}",email);
        return userRepository.findByEmail(email).get();
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public User getUserFromToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String access_token = authorizationHeader.substring("Bearer ".length());   // we pass in how many letters we want to remove, we cut the bearer to get the token
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build(); // I need to pass in the algorithm to that and then I need to call build
        DecodedJWT decodedJWT = verifier.verify(access_token);
                // String username = decodedJWT.getSubject();
        String email = decodedJWT.getSubject();
        return getUser(email);        // User user = getUser(username);
        // return userRepository.findByEmail(email).get();
    }

    // this method that Spring uses to load the users from database or wherever they might be
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).get();
        if(user == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.error("User found in the database: {}",user.getName());
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        // need to return User here, that is the Spring Security user coming from the core UserDetails user
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
