import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './absence.reducer';
import { IAbsence } from 'app/shared/model/absence.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAbsenceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AbsenceDetail = (props: IAbsenceDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { absenceEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Absence [<b>{absenceEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="from">From</span>
          </dt>
          <dd>
            <TextFormat value={absenceEntity.from} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="to">To</span>
          </dt>
          <dd>
            <TextFormat value={absenceEntity.to} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="phoneNumber">Phone Number</span>
          </dt>
          <dd>{absenceEntity.phoneNumber}</dd>
          <dt>
            <span id="motivation">Motivation</span>
          </dt>
          <dd>{absenceEntity.motivation}</dd>
          <dt>Employee</dt>
          <dd>{absenceEntity.employee ? absenceEntity.employee.id : ''}</dd>
          <dt>Type</dt>
          <dd>{absenceEntity.type ? absenceEntity.type.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/absence" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/absence/${absenceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ absence }: IRootState) => ({
  absenceEntity: absence.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AbsenceDetail);
