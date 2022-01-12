import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TransactionDuplicates from './transaction-duplicates';
import TransactionDuplicatesDetail from './transaction-duplicates-detail';
import TransactionDuplicatesUpdate from './transaction-duplicates-update';
import TransactionDuplicatesDeleteDialog from './transaction-duplicates-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TransactionDuplicatesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TransactionDuplicatesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TransactionDuplicatesDetail} />
      <ErrorBoundaryRoute path={match.url} component={TransactionDuplicates} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TransactionDuplicatesDeleteDialog} />
  </>
);

export default Routes;
