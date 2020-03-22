import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './door.reducer';
import { IDoor } from 'app/shared/model/door.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDoorDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DoorDetail = (props: IDoorDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { doorEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Door [<b>{doorEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="doorName">Door Name</span>
          </dt>
          <dd>{doorEntity.doorName}</dd>
        </dl>
        <Button tag={Link} to="/door" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/door/${doorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ door }: IRootState) => ({
  doorEntity: door.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DoorDetail);
