import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BridgeRun from './bridge-run';
import BridgeRunDetail from './bridge-run-detail';
import BridgeRunUpdate from './bridge-run-update';
import BridgeRunDeleteDialog from './bridge-run-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BridgeRunUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BridgeRunUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BridgeRunDetail} />
      <ErrorBoundaryRoute path={match.url} component={BridgeRun} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BridgeRunDeleteDialog} />
  </>
);

export default Routes;
