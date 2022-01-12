import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities, reset } from './plaid-transaction.reducer';
import { IPlaidTransaction } from 'app/shared/model/plaid-transaction.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PlaidTransaction = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const plaidTransactionList = useAppSelector(state => state.plaidTransaction.entities);
  const loading = useAppSelector(state => state.plaidTransaction.loading);
  const totalItems = useAppSelector(state => state.plaidTransaction.totalItems);
  const links = useAppSelector(state => state.plaidTransaction.links);
  const entity = useAppSelector(state => state.plaidTransaction.entity);
  const updateSuccess = useAppSelector(state => state.plaidTransaction.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="plaid-transaction-heading" data-cy="PlaidTransactionHeading">
        <Translate contentKey="akountsApp.plaidTransaction.home.title">Plaid Transactions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="akountsApp.plaidTransaction.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="akountsApp.plaidTransaction.home.createLabel">Create new Plaid Transaction</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={plaidTransactionList ? plaidTransactionList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {plaidTransactionList && plaidTransactionList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="akountsApp.plaidTransaction.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('transactionId')}>
                    <Translate contentKey="akountsApp.plaidTransaction.transactionId">Transaction Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('transactionType')}>
                    <Translate contentKey="akountsApp.plaidTransaction.transactionType">Transaction Type</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('accountId')}>
                    <Translate contentKey="akountsApp.plaidTransaction.accountId">Account Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('amount')}>
                    <Translate contentKey="akountsApp.plaidTransaction.amount">Amount</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('isoCurrencyCode')}>
                    <Translate contentKey="akountsApp.plaidTransaction.isoCurrencyCode">Iso Currency Code</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('transactionDate')}>
                    <Translate contentKey="akountsApp.plaidTransaction.transactionDate">Transaction Date</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('name')}>
                    <Translate contentKey="akountsApp.plaidTransaction.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('originalDescription')}>
                    <Translate contentKey="akountsApp.plaidTransaction.originalDescription">Original Description</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('pending')}>
                    <Translate contentKey="akountsApp.plaidTransaction.pending">Pending</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('pendingTransactionId')}>
                    <Translate contentKey="akountsApp.plaidTransaction.pendingTransactionId">Pending Transaction Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('addedDate')}>
                    <Translate contentKey="akountsApp.plaidTransaction.addedDate">Added Date</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('checked')}>
                    <Translate contentKey="akountsApp.plaidTransaction.checked">Checked</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {plaidTransactionList.map((plaidTransaction, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`${match.url}/${plaidTransaction.id}`} color="link" size="sm">
                        {plaidTransaction.id}
                      </Button>
                    </td>
                    <td>{plaidTransaction.transactionId}</td>
                    <td>{plaidTransaction.transactionType}</td>
                    <td>{plaidTransaction.accountId}</td>
                    <td>{plaidTransaction.amount}</td>
                    <td>{plaidTransaction.isoCurrencyCode}</td>
                    <td>
                      {plaidTransaction.transactionDate ? (
                        <TextFormat type="date" value={plaidTransaction.transactionDate} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{plaidTransaction.name}</td>
                    <td>{plaidTransaction.originalDescription}</td>
                    <td>{plaidTransaction.pending ? 'true' : 'false'}</td>
                    <td>{plaidTransaction.pendingTransactionId}</td>
                    <td>
                      {plaidTransaction.addedDate ? (
                        <TextFormat type="date" value={plaidTransaction.addedDate} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{plaidTransaction.checked ? 'true' : 'false'}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${plaidTransaction.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`${match.url}/${plaidTransaction.id}/edit`}
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
                          to={`${match.url}/${plaidTransaction.id}/delete`}
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
                <Translate contentKey="akountsApp.plaidTransaction.home.notFound">No Plaid Transactions found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default PlaidTransaction;
