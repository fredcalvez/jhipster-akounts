import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PlaidRun from './plaid-run';
import PlaidRunDetail from './plaid-run-detail';
import PlaidRunUpdate from './plaid-run-update';
import PlaidRunDeleteDialog from './plaid-run-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PlaidRunUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PlaidRunUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PlaidRunDetail} />
      <ErrorBoundaryRoute path={match.url} component={PlaidRun} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PlaidRunDeleteDialog} />
  </>
);

export default Routes;
