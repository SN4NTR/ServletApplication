package com.leverx.servletapp.user.service;

import com.leverx.servletapp.core.exception.EntityNotFoundException;
import com.leverx.servletapp.core.exception.TransferException;
import com.leverx.servletapp.core.exception.ValidationException;
import com.leverx.servletapp.user.converter.UserConverter;
import com.leverx.servletapp.user.dto.UserInputDto;
import com.leverx.servletapp.user.dto.UserOutputDto;
import com.leverx.servletapp.user.dto.UserTransferDto;
import com.leverx.servletapp.user.dto.UserWithAnimalsDto;
import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.repository.UserRepository;
import com.leverx.servletapp.user.validator.UserValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Min;
import java.util.Collection;

import static com.leverx.servletapp.core.exception.ErrorConstant.TRANSFER_ERROR;
import static com.leverx.servletapp.core.exception.ErrorConstant.getLocalizedMessage;
import static com.leverx.servletapp.user.converter.UserConverter.fromInputDto;
import static com.leverx.servletapp.web.HttpResponseStatus.UNPROCESSABLE_ENTITY;

@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserValidator userValidator;

    @Override
    public void save(UserInputDto userInputDto) throws ValidationException, EntityNotFoundException {
        userValidator.validateInputDto(userInputDto);
        var user = fromInputDto(userInputDto);
        userRepository.save(user);
    }

    @Override
    public void delete(int id) throws EntityNotFoundException {
        userValidator.validateId(id);
        userRepository.delete(id);
    }

    @Override
    public void update(int id, UserInputDto userInputDto) throws EntityNotFoundException, ValidationException {
        userValidator.validateInputDto(userInputDto);
        userValidator.validateId(id);

        var userOpt = userRepository.findById(id);
        var user = userOpt.orElseThrow(EntityNotFoundException::new);
        var userFromDto = fromInputDto(userInputDto);
        var existingAnimals = user.getAnimals();
        var newAnimals = userFromDto.getAnimals();
        newAnimals.addAll(existingAnimals);

        userFromDto.setId(id);
        userRepository.update(userFromDto);
    }

    @Override
    public void transferAnimalPoints(int senderId, UserTransferDto userTransferDto) throws EntityNotFoundException, TransferException, ValidationException {
        userValidator.validateTransferDto(senderId, userTransferDto);
        var receiverId = userTransferDto.getReceiverId();
        var animalPoints = userTransferDto.getAnimalPoints();

        var senderOpt = userRepository.findById(senderId);
        var sender = senderOpt.orElseThrow(EntityNotFoundException::new);
        var receiverOpt = userRepository.findById(receiverId);
        var receiver = receiverOpt.orElseThrow(EntityNotFoundException::new);

        doTransfer(animalPoints, sender, receiver);
    }

    @Override
    public UserWithAnimalsDto findById(int id) throws EntityNotFoundException {
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

    private void doTransfer(int animalPoints, User sender, User receiver) {
        var senderAnimalPointsAccount = sender.getAnimalPoints();
        sender.setAnimalPoints(senderAnimalPointsAccount - animalPoints);
        var receiverAnimalPointsAccount = receiver.getAnimalPoints();
        receiver.setAnimalPoints(receiverAnimalPointsAccount + animalPoints);
        userRepository.update(sender);
        userRepository.update(receiver);
    }
}
