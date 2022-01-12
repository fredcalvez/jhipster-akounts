import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProcessRun from './process-run';
import ProcessRunDetail from './process-run-detail';
import ProcessRunUpdate from './process-run-update';
import ProcessRunDeleteDialog from './process-run-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProcessRunUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProcessRunUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProcessRunDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProcessRun} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProcessRunDeleteDialog} />
  </>
);

export default Routes;
