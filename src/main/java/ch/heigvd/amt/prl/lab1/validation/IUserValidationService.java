package ch.heigvd.amt.prl.lab1.validation;

import ch.heigvd.amt.prl.lab1.dto.UserWriteDto;
import javax.ejb.Local;

/**
 * The validation service for a user
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
@Local
public interface IUserValidationService extends IValidationService<UserWriteDto> {
}
