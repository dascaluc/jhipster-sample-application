import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { byteSize, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, reset } from './absence.reducer';
import { IAbsence } from 'app/shared/model/absence.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IAbsenceProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Absence = (props: IAbsenceProps) => {
  const [paginationState, setPaginationState] = useState(getSortState(props.location, ITEMS_PER_PAGE));
  const [sorting, setSorting] = useState(false);

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const resetAll = () => {
    props.reset();
    setPaginationState({
      ...paginationState,
      activePage: 1
    });
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if (window.pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    props.reset();
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p
    });
    setSorting(true);
  };

  const { absenceList, match, loading } = props;
  return (
    <div>
      <h2 id="absence-heading">
        Absences
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Absence
        </Link>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          pageStart={paginationState.activePage}
          loadMore={handleLoadMore}
          hasMore={paginationState.activePage - 1 < props.links.next}
          loader={<div className="loader">Loading ...</div>}
          threshold={0}
          initialLoad={false}
        >
          {absenceList && absenceList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('from')}>
                    From <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('to')}>
                    To <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('phoneNumber')}>
                    Phone Number <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('motivation')}>
                    Motivation <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Employee <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Type <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {absenceList.map((absence, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${absence.id}`} color="link" size="sm">
                        {absence.id}
                      </Button>
                    </td>
                    <td>
                      <TextFormat type="date" value={absence.from} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={absence.to} format={APP_DATE_FORMAT} />
                    </td>
                    <td>{absence.phoneNumber}</td>
                    <td>{absence.motivation}</td>
                    <td>{absence.employee ? <Link to={`employee/${absence.employee.id}`}>{absence.employee.id}</Link> : ''}</td>
                    <td>{absence.type ? <Link to={`absence-type/${absence.type.id}`}>{absence.type.id}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${absence.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${absence.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${absence.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && <div className="alert alert-warning">No Absences found</div>
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

const mapStateToProps = ({ absence }: IRootState) => ({
  absenceList: absence.entities,
  loading: absence.loading,
  totalItems: absence.totalItems,
  links: absence.links,
  entity: absence.entity,
  updateSuccess: absence.updateSuccess
});

const mapDispatchToProps = {
  getEntities,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Absence);
