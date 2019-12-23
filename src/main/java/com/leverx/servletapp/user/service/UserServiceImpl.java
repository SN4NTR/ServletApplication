package com.leverx.servletapp.user.service;

import com.leverx.servletapp.core.exception.EntityNotFoundException;
import com.leverx.servletapp.core.exception.TransferException;
import com.leverx.servletapp.core.exception.ValidationException;
import com.leverx.servletapp.user.converter.UserConverter;
import com.leverx.servletapp.user.dto.UserInputDto;
import com.leverx.servletapp.user.dto.UserOutputDto;
import com.leverx.servletapp.user.dto.UserWithAnimalsDto;
import com.leverx.servletapp.user.repository.UserRepository;
import com.leverx.servletapp.user.validator.UserValidator;
import com.leverx.servletapp.web.HttpResponseStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

import static com.leverx.servletapp.core.exception.ErrorConstant.TRANSFER_ERROR;
import static com.leverx.servletapp.core.exception.ErrorConstant.getLocalizedMessage;
import static com.leverx.servletapp.user.converter.UserConverter.fromInputDto;
import static com.leverx.servletapp.user.validator.UserValidator.validateInputDto;
import static com.leverx.servletapp.web.HttpResponseStatus.UNPROCESSABLE_ENTITY;

@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public void save(UserInputDto userInputDto) throws ValidationException, EntityNotFoundException {
        validateInputDto(userInputDto);
        var user = fromInputDto(userInputDto);
        userRepository.save(user);
    }

    @Override
    public void delete(int id) throws EntityNotFoundException {
        var userValidator = new UserValidator(userRepository);
        userValidator.validateId(id);
        userRepository.delete(id);
    }

    @Override
    public void update(int id, UserInputDto userInputDto) throws EntityNotFoundException, ValidationException {
        var userValidator = new UserValidator(userRepository);
        userValidator.validateId(id);
        validateInputDto(userInputDto);

        var userOpt = userRepository.findById(id);
        var user = userOpt.orElseThrow(EntityNotFoundException::new);
        var userFromDto = fromInputDto(userInputDto);
        var existingAnimals = user.getAnimals();
        var newAnimals = userFromDto.getAnimals();
        existingAnimals.addAll(newAnimals);

        user.setId(id);
        userRepository.update(user);
    }

    @Override
    public void transferAnimalPoints(int senderId, int receiverId, int animalPoints) throws EntityNotFoundException, TransferException {
        var userValidator = new UserValidator(userRepository);
        userValidator.validateId(senderId);
        userValidator.validateId(receiverId);

        var senderOpt = userRepository.findById(senderId);
        var sender = senderOpt.orElseThrow(EntityNotFoundException::new);
        var receiverOpt = userRepository.findById(receiverId);
        var receiver = receiverOpt.orElseThrow(EntityNotFoundException::new);

        var senderAnimalPointsAccount = sender.getAnimalPoints();
        if (senderAnimalPointsAccount >= animalPoints) {
            sender.setAnimalPoints(senderAnimalPointsAccount - animalPoints);
            var receiverAnimalPointsAccount = receiver.getAnimalPoints();
            receiver.setAnimalPoints(receiverAnimalPointsAccount + animalPoints);
            userRepository.update(sender);
            userRepository.update(receiver);
        } else {
            var message = getLocalizedMessage(TRANSFER_ERROR);
            throw new TransferException(message, UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public UserWithAnimalsDto findById(int id) throws EntityNotFoundException {
        var userValidator = new UserValidator(userRepository);
        userValidator.validateId(id);
        var userOpt = userRepository.findById(id);
        var user = userOpt.orElseThrow(EntityNotFoundException::new);
        return UserConverter.toWithAnimalsDto(user);
    }

    @Override
    public Collection<UserOutputDto> findByName(String name) {
        var users = userRepository.findByName(name);
        return UserConverter.toOutputDtoList(users);
    }

    @Override
    public Collection<UserOutputDto> findAll() {
        var users = userRepository.findAll();
        return UserConverter.toOutputDtoList(users);
    }
}
