import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IDoor } from 'app/shared/model/door.model';
import { getEntities as getDoors } from 'app/entities/door/door.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { getEntity, updateEntity, createEntity, reset } from './time-tracking.reducer';
import { ITimeTracking } from 'app/shared/model/time-tracking.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITimeTrackingUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TimeTrackingUpdate = (props: ITimeTrackingUpdateProps) => {
  const [doorId, setDoorId] = useState('0');
  const [employeeId, setEmployeeId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { timeTrackingEntity, doors, employees, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/time-tracking');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getDoors();
    props.getEmployees();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.date = convertDateTimeToServer(values.date);

    if (errors.length === 0) {
      const entity = {
        ...timeTrackingEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterSampleApplicationApp.timeTracking.home.createOrEditLabel">Create or edit a TimeTracking</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : timeTrackingEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="time-tracking-id">ID</Label>
                  <AvInput id="time-tracking-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="dateLabel" for="time-tracking-date">
                  Date
                </Label>
                <AvInput
                  id="time-tracking-date"
                  type="datetime-local"
                  className="form-control"
                  name="date"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.timeTrackingEntity.date)}
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="accessTypeeLabel" for="time-tracking-accessTypee">
                  Access Typee
                </Label>
                <AvInput
                  id="time-tracking-accessTypee"
                  type="select"
                  className="form-control"
                  name="accessTypee"
                  value={(!isNew && timeTrackingEntity.accessTypee) || 'IN'}
                >
                  <option value="IN">IN</option>
                  <option value="OUT">OUT</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="time-tracking-description">
                  Description
                </Label>
                <AvField id="time-tracking-description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label for="time-tracking-door">Door</Label>
                <AvInput id="time-tracking-door" type="select" className="form-control" name="door.id">
                  <option value="" key="0" />
                  {doors
                    ? doors.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="time-tracking-employee">Employee</Label>
                <AvInput id="time-tracking-employee" type="select" className="form-control" name="employee.id">
                  <option value="" key="0" />
                  {employees
                    ? employees.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/time-tracking" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  doors: storeState.door.entities,
  employees: storeState.employee.entities,
  timeTrackingEntity: storeState.timeTracking.entity,
  loading: storeState.timeTracking.loading,
  updating: storeState.timeTracking.updating,
  updateSuccess: storeState.timeTracking.updateSuccess
});

const mapDispatchToProps = {
  getDoors,
  getEmployees,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TimeTrackingUpdate);
