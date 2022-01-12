import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BankStream from './bank-stream';
import BankStreamDetail from './bank-stream-detail';
import BankStreamUpdate from './bank-stream-update';
import BankStreamDeleteDialog from './bank-stream-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BankStreamUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BankStreamUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BankStreamDetail} />
      <ErrorBoundaryRoute path={match.url} component={BankStream} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BankStreamDeleteDialog} />
  </>
);

export default Routes;
