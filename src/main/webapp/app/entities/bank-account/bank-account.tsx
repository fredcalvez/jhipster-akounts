import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './bank-account.reducer';
import { IBankAccount } from 'app/shared/model/bank-account.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankAccount = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const bankAccountList = useAppSelector(state => state.bankAccount.entities);
  const loading = useAppSelector(state => state.bankAccount.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="bank-account-heading" data-cy="BankAccountHeading">
        <Translate contentKey="akountsApp.bankAccount.home.title">Bank Accounts</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="akountsApp.bankAccount.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="akountsApp.bankAccount.home.createLabel">Create new Bank Account</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bankAccountList && bankAccountList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="akountsApp.bankAccount.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankAccount.accountLabel">Account Label</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankAccount.accountNumber">Account Number</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankAccount.active">Active</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankAccount.currency">Currency</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankAccount.initialAmount">Initial Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankAccount.initialAmountLocal">Initial Amount Local</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankAccount.accountType">Account Type</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankAccount.interest">Interest</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankAccount.nickname">Nickname</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankAccount.institution">Institution</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bankAccountList.map((bankAccount, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${bankAccount.id}`} color="link" size="sm">
                      {bankAccount.id}
                    </Button>
                  </td>
                  <td>{bankAccount.accountLabel}</td>
                  <td>{bankAccount.accountNumber}</td>
                  <td>{bankAccount.active ? 'true' : 'false'}</td>
                  <td>
                    <Translate contentKey={`akountsApp.Currency.${bankAccount.currency}`} />
                  </td>
                  <td>{bankAccount.initialAmount}</td>
                  <td>{bankAccount.initialAmountLocal}</td>
                  <td>
                    <Translate contentKey={`akountsApp.AccountType.${bankAccount.accountType}`} />
                  </td>
                  <td>{bankAccount.interest}</td>
                  <td>{bankAccount.nickname}</td>
                  <td>
                    {bankAccount.institution ? (
                      <Link to={`bank-institution/${bankAccount.institution.id}`}>{bankAccount.institution.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${bankAccount.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bankAccount.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bankAccount.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="akountsApp.bankAccount.home.notFound">No Bank Accounts found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default BankAccount;
