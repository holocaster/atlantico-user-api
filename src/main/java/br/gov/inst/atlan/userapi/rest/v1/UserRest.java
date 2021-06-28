package br.gov.inst.atlan.userapi.rest.v1;

import br.gov.inst.atlan.userapi.rest.v1.dto.UserDTO;
import br.gov.inst.atlan.userapi.rest.v1.dto.UserDTOPagedList;
import br.gov.inst.atlan.userapi.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@Slf4j
@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserRest {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 10;

    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTOPagedList> listUsers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        UserDTOPagedList userDTOs = this.userService.listUsers(PageRequest.of(pageNumber, pageSize));

        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> findById(@PathVariable("userId") UUID userId) {
        return ResponseEntity.ok(this.userService.findById(userId));
    }

    @PostMapping()
    public ResponseEntity<UserDTO> insertUser(@Valid @RequestBody UserDTO userDTO) {

        final UserDTO saveDTO = this.userService.insertUser(userDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saveDTO.getId().toString()).toUri();
        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/{userId}")
    public ResponseEntity updateUser(@PathVariable("userId") UUID userId, @Valid @RequestBody UserDTO userDTO) {
        this.userService.updateUser(userId, userDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable("userId") UUID userId) {
        this.userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
