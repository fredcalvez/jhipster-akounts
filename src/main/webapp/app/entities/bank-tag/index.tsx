import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BankTag from './bank-tag';
import BankTagDetail from './bank-tag-detail';
import BankTagUpdate from './bank-tag-update';
import BankTagDeleteDialog from './bank-tag-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BankTagUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BankTagUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BankTagDetail} />
      <ErrorBoundaryRoute path={match.url} component={BankTag} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BankTagDeleteDialog} />
  </>
);

export default Routes;
