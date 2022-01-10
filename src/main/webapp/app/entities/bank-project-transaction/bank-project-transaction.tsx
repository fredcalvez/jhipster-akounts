import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './bank-project-transaction.reducer';
import { IBankProjectTransaction } from 'app/shared/model/bank-project-transaction.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankProjectTransaction = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const bankProjectTransactionList = useAppSelector(state => state.bankProjectTransaction.entities);
  const loading = useAppSelector(state => state.bankProjectTransaction.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="bank-project-transaction-heading" data-cy="BankProjectTransactionHeading">
        <Translate contentKey="akountsApp.bankProjectTransaction.home.title">Bank Project Transactions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="akountsApp.bankProjectTransaction.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="akountsApp.bankProjectTransaction.home.createLabel">Create new Bank Project Transaction</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bankProjectTransactionList && bankProjectTransactionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="akountsApp.bankProjectTransaction.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankProjectTransaction.transactionId">Transaction Id</Translate>
                </th>
                <th>
                  <Translate contentKey="akountsApp.bankProjectTransaction.projectId">Project Id</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bankProjectTransactionList.map((bankProjectTransaction, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${bankProjectTransaction.id}`} color="link" size="sm">
                      {bankProjectTransaction.id}
                    </Button>
                  </td>
                  <td>
                    {bankProjectTransaction.transactionId ? (
                      <Link to={`bank-transaction/${bankProjectTransaction.transactionId.id}`}>
                        {bankProjectTransaction.transactionId.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {bankProjectTransaction.projectId ? (
                      <Link to={`bank-project/${bankProjectTransaction.projectId.id}`}>{bankProjectTransaction.projectId.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${bankProjectTransaction.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${bankProjectTransaction.id}/edit`}
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
                        to={`${match.url}/${bankProjectTransaction.id}/delete`}
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
              <Translate contentKey="akountsApp.bankProjectTransaction.home.notFound">No Bank Project Transactions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default BankProjectTransaction;
