import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PlaidItem from './plaid-item';
import PlaidItemDetail from './plaid-item-detail';
import PlaidItemUpdate from './plaid-item-update';
import PlaidItemDeleteDialog from './plaid-item-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PlaidItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PlaidItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PlaidItemDetail} />
      <ErrorBoundaryRoute path={match.url} component={PlaidItem} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PlaidItemDeleteDialog} />
  </>
);

export default Routes;
