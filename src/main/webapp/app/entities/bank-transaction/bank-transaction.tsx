import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './bank-transaction.reducer';
import { IBankTransaction } from 'app/shared/model/bank-transaction.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankTransaction = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const bankTransactionList = useAppSelector(state => state.bankTransaction.entities);
  const loading = useAppSelector(state => state.bankTransaction.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="bank-transaction-heading" data-cy="BankTransactionHeading">
        <Translate contentKey="akountsApp.bankTransaction.home.title">Bank Transactions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="akountsApp.bankTransaction.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="akountsApp.bankTransaction.home.createLabel">Create new Bank Transaction</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bankTransactionList && bankTransactionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="akountsApp.bankTransaction.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankTransaction.transactionId">Transaction Id</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankTransaction.transactionDate">Transaction Date</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankTransaction.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankTransaction.localAmount">Local Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankTransaction.localCurrency">Local Currency</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankTransaction.amount">Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankTransaction.currency">Currency</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankTransaction.note">Note</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankTransaction.year">Year</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankTransaction.month">Month</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankTransaction.week">Week</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankTransaction.categorizedDate">Categorized Date</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankTransaction.addDate">Add Date</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankTransaction.checked">Checked</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankTransaction.rebasedDate">Rebased Date</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankTransaction.deleted">Deleted</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankTransaction.tag">Tag</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankTransaction.createdOn">Created On</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankTransaction.updatedOn">Updated On</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankTransaction.version">Version</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bankTransactionList.map((bankTransaction, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${bankTransaction.id}`} color="link" size="sm">
                      {bankTransaction.id}
                    </Button>
                  </td>
                  <td>{bankTransaction.transactionId}</td>
                  <td>
                    {bankTransaction.transactionDate ? (
                      <TextFormat type="date" value={bankTransaction.transactionDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{bankTransaction.description}</td>
                  <td>{bankTransaction.localAmount}</td>
                  <td>
                    <Translate contentKey={`akountsApp.Currency.${bankTransaction.localCurrency}`} />
                  </td>
                  <td>{bankTransaction.amount}</td>
                  <td>
                    <Translate contentKey={`akountsApp.Currency.${bankTransaction.currency}`} />
                  </td>
                  <td>{bankTransaction.note}</td>
                  <td>{bankTransaction.year}</td>
                  <td>{bankTransaction.month}</td>
                  <td>{bankTransaction.week}</td>
                  <td>
                    {bankTransaction.categorizedDate ? (
                      <TextFormat type="date" value={bankTransaction.categorizedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {bankTransaction.addDate ? <TextFormat type="date" value={bankTransaction.addDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{bankTransaction.checked ? 'true' : 'false'}</td>
                  <td>
                    {bankTransaction.rebasedDate ? (
                      <TextFormat type="date" value={bankTransaction.rebasedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{bankTransaction.deleted ? 'true' : 'false'}</td>
                  <td>{bankTransaction.tag}</td>
                  <td>
                    {bankTransaction.createdOn ? (
                      <TextFormat type="date" value={bankTransaction.createdOn} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {bankTransaction.updatedOn ? (
                      <TextFormat type="date" value={bankTransaction.updatedOn} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{bankTransaction.version}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${bankTransaction.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${bankTransaction.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${bankTransaction.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="akountsApp.bankTransaction.home.notFound">No Bank Transactions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default BankTransaction;
