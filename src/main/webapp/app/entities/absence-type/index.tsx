import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AbsenceType from './absence-type';
import AbsenceTypeDetail from './absence-type-detail';
import AbsenceTypeUpdate from './absence-type-update';
import AbsenceTypeDeleteDialog from './absence-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AbsenceTypeDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AbsenceTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AbsenceTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AbsenceTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={AbsenceType} />
    </Switch>
  </>
);

export default Routes;
