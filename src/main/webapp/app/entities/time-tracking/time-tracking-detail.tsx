import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './time-tracking.reducer';
import { ITimeTracking } from 'app/shared/model/time-tracking.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITimeTrackingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TimeTrackingDetail = (props: ITimeTrackingDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { timeTrackingEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          TimeTracking [<b>{timeTrackingEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>
            <TextFormat value={timeTrackingEntity.date} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="accessTypee">Access Typee</span>
          </dt>
          <dd>{timeTrackingEntity.accessTypee}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{timeTrackingEntity.description}</dd>
          <dt>Door</dt>
          <dd>{timeTrackingEntity.door ? timeTrackingEntity.door.id : ''}</dd>
          <dt>Employee</dt>
          <dd>{timeTrackingEntity.employee ? timeTrackingEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/time-tracking" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/time-tracking/${timeTrackingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ timeTracking }: IRootState) => ({
  timeTrackingEntity: timeTracking.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TimeTrackingDetail);
