package pl.edu.pwr.programming_technologies.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pwr.programming_technologies.exceptions.EntityConflictException;
import pl.edu.pwr.programming_technologies.exceptions.EntityNotFoundException;
import pl.edu.pwr.programming_technologies.model.entity.primary.UserEntity;
import pl.edu.pwr.programming_technologies.repository.primary.UserRepository;
import pl.edu.pwr.programming_technologies.service.UserService;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity getUserByUserAccountId(String userAccountId) throws EntityNotFoundException {

        Optional<UserEntity> foundUserAccountId = userRepository.findByUserAccountId(userAccountId);

        if(foundUserAccountId.isEmpty()){
            throw new EntityNotFoundException("Nie istnieje konto o takim id");
        }

        return foundUserAccountId.get();
    }

    @Override
    public boolean existsUserByNickname(String nickname){

        return userRepository.existsByNickname(nickname);
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) throws IllegalArgumentException, EntityConflictException{

        if(userEntity == null) {
            throw new IllegalArgumentException("Nie poddano danych");
        }

        if(userEntity.getUserAccountId() == null){
            throw new IllegalArgumentException("Nie podano id konta");
        }

        if(userEntity.getNickname() == null){
            throw new IllegalArgumentException("Nie podano pseudonimu");
        }

        if(userRepository.existsByUserAccountId(userEntity.getUserAccountId())){
            throw new EntityConflictException("Istnieje już użytkownik o takim id konta");
        }

        if(userRepository.existsByNickname(userEntity.getNickname())){
            throw new EntityConflictException("Istnieje już użytkownik o takim pseudonimie");
        }

        return userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public UserEntity updateUser(Integer userId, UserEntity userEntity)
            throws IllegalArgumentException, EntityNotFoundException, EntityConflictException
    {

        if(userEntity == null){
            throw new IllegalArgumentException("Nie poddano danych");
        }

        Optional<UserEntity> foundUserOpt = userRepository.findById(userId);

        if(foundUserOpt.isEmpty()){
            throw new EntityNotFoundException("Nie istnieje użytkownik o takim id");
        }

        if(existsUserByNickname(userEntity.getNickname())){
            throw new EntityConflictException("Istnieje już użytkownik o takim pseudonimie");
        }

        UserEntity foundUser = foundUserOpt.get();

        if(userEntity.getNickname() != null){
            foundUser.setNickname(userEntity.getNickname());
        }

        if(userEntity.getFirstname() != null){
            foundUser.setFirstname(userEntity.getFirstname());
        }

        if(userEntity.getSurname() != null){
            foundUser.setSurname(userEntity.getSurname());
        }

        if(userEntity.getEmail() != null){
            foundUser.setEmail(userEntity.getEmail());
        }

        if(userEntity.getAvatar() != null){
            foundUser.setAvatar(userEntity.getAvatar());
        }

        return foundUser;
    }
}
