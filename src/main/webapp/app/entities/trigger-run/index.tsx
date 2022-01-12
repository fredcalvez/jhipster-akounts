import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TriggerRun from './trigger-run';
import TriggerRunDetail from './trigger-run-detail';
import TriggerRunUpdate from './trigger-run-update';
import TriggerRunDeleteDialog from './trigger-run-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TriggerRunUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TriggerRunUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TriggerRunDetail} />
      <ErrorBoundaryRoute path={match.url} component={TriggerRun} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TriggerRunDeleteDialog} />
  </>
);

export default Routes;
