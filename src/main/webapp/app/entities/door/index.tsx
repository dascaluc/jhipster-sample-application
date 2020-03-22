import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Door from './door';
import DoorDetail from './door-detail';
import DoorUpdate from './door-update';
import DoorDeleteDialog from './door-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DoorDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DoorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DoorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DoorDetail} />
      <ErrorBoundaryRoute path={match.url} component={Door} />
    </Switch>
  </>
);

export default Routes;
