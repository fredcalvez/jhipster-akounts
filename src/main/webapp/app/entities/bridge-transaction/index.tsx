import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BridgeTransaction from './bridge-transaction';
import BridgeTransactionDetail from './bridge-transaction-detail';
import BridgeTransactionUpdate from './bridge-transaction-update';
import BridgeTransactionDeleteDialog from './bridge-transaction-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BridgeTransactionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BridgeTransactionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BridgeTransactionDetail} />
      <ErrorBoundaryRoute path={match.url} component={BridgeTransaction} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BridgeTransactionDeleteDialog} />
  </>
);

export default Routes;
