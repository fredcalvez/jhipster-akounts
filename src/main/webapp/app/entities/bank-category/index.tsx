import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BankCategory from './bank-category';
import BankCategoryDetail from './bank-category-detail';
import BankCategoryUpdate from './bank-category-update';
import BankCategoryDeleteDialog from './bank-category-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BankCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BankCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BankCategoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={BankCategory} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BankCategoryDeleteDialog} />
  </>
);

export default Routes;
