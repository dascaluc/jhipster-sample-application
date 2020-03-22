import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TimeTracking from './time-tracking';
import Employee from './employee';
import Door from './door';
import Absence from './absence';
import AbsenceType from './absence-type';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}time-tracking`} component={TimeTracking} />
      <ErrorBoundaryRoute path={`${match.url}employee`} component={Employee} />
      <ErrorBoundaryRoute path={`${match.url}door`} component={Door} />
      <ErrorBoundaryRoute path={`${match.url}absence`} component={Absence} />
      <ErrorBoundaryRoute path={`${match.url}absence-type`} component={AbsenceType} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
