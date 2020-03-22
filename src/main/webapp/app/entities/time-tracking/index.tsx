import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TimeTracking from './time-tracking';
import TimeTrackingDetail from './time-tracking-detail';
import TimeTrackingUpdate from './time-tracking-update';
import TimeTrackingDeleteDialog from './time-tracking-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TimeTrackingDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TimeTrackingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TimeTrackingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TimeTrackingDetail} />
      <ErrorBoundaryRoute path={match.url} component={TimeTracking} />
    </Switch>
  </>
);

export default Routes;
