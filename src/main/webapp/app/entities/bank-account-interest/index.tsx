import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BankAccountInterest from './bank-account-interest';
import BankAccountInterestDetail from './bank-account-interest-detail';
import BankAccountInterestUpdate from './bank-account-interest-update';
import BankAccountInterestDeleteDialog from './bank-account-interest-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BankAccountInterestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BankAccountInterestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BankAccountInterestDetail} />
      <ErrorBoundaryRoute path={match.url} component={BankAccountInterest} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BankAccountInterestDeleteDialog} />
  </>
);

export default Routes;
