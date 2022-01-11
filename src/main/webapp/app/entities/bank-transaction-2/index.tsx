import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BankTransaction2 from './bank-transaction-2';
import BankTransaction2Detail from './bank-transaction-2-detail';
import BankTransaction2Update from './bank-transaction-2-update';
import BankTransaction2DeleteDialog from './bank-transaction-2-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BankTransaction2Update} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BankTransaction2Update} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BankTransaction2Detail} />
      <ErrorBoundaryRoute path={match.url} component={BankTransaction2} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BankTransaction2DeleteDialog} />
  </>
);

export default Routes;
