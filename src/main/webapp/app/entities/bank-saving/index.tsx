import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BankSaving from './bank-saving';
import BankSavingDetail from './bank-saving-detail';
import BankSavingUpdate from './bank-saving-update';
import BankSavingDeleteDialog from './bank-saving-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BankSavingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BankSavingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BankSavingDetail} />
      <ErrorBoundaryRoute path={match.url} component={BankSaving} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BankSavingDeleteDialog} />
  </>
);

export default Routes;
