package net.explorviz.discovery.exceptions.mapper.procezz;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import net.explorviz.discovery.exceptions.mapper.ResponseUtil;
import net.explorviz.discovery.exceptions.procezz.ProcezzManagementTypeIncompatibleException;
import net.explorviz.discovery.model.helper.ErrorObjectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcezzManagementTypeIncompatibleMapper
    implements ExceptionMapper<ProcezzManagementTypeIncompatibleException> {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(ProcezzManagementTypeIncompatibleMapper.class);

  @Override
  public Response toResponse(final ProcezzManagementTypeIncompatibleException exception) {

    LOGGER.error("Error occured while comparing procezzes. Error: {}", exception.getMessage());

    if (exception.getFaultyProcezz() != null) {
      exception.getFaultyProcezz().setErrorOccured(true);
      exception.getFaultyProcezz().setErrorMessage(exception.getMessage());
    }

    final byte[] errorObject = ErrorObjectHelper.getInstance().createSerializedErrorArray(
        ResponseUtil.HTTP_STATUS_UNPROCESSABLE_ENTITY, ResponseUtil.ERROR_INTERNAL_AGENT,
        exception.getMessage());
    return Response.status(422).entity(errorObject).build();
  }

}
