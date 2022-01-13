import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities, reset } from './bridge-transaction.reducer';
import { IBridgeTransaction } from 'app/shared/model/bridge-transaction.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BridgeTransaction = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const bridgeTransactionList = useAppSelector(state => state.bridgeTransaction.entities);
  const loading = useAppSelector(state => state.bridgeTransaction.loading);
  const totalItems = useAppSelector(state => state.bridgeTransaction.totalItems);
  const links = useAppSelector(state => state.bridgeTransaction.links);
  const entity = useAppSelector(state => state.bridgeTransaction.entity);
  const updateSuccess = useAppSelector(state => state.bridgeTransaction.updateSuccess);

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
      <h2 id="bridge-transaction-heading" data-cy="BridgeTransactionHeading">
        <Translate contentKey="akountsApp.bridgeTransaction.home.title">Bridge Transactions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="akountsApp.bridgeTransaction.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="akountsApp.bridgeTransaction.home.createLabel">Create new Bridge Transaction</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={bridgeTransactionList ? bridgeTransactionList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {bridgeTransactionList && bridgeTransactionList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="akountsApp.bridgeTransaction.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('transactionId')}>
                    <Translate contentKey="akountsApp.bridgeTransaction.transactionId">Transaction Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('transactionType')}>
                    <Translate contentKey="akountsApp.bridgeTransaction.transactionType">Transaction Type</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('accountId')}>
                    <Translate contentKey="akountsApp.bridgeTransaction.accountId">Account Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('amount')}>
                    <Translate contentKey="akountsApp.bridgeTransaction.amount">Amount</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('isoCurrencyCode')}>
                    <Translate contentKey="akountsApp.bridgeTransaction.isoCurrencyCode">Iso Currency Code</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('transactionDate')}>
                    <Translate contentKey="akountsApp.bridgeTransaction.transactionDate">Transaction Date</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('description')}>
                    <Translate contentKey="akountsApp.bridgeTransaction.description">Description</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('isFuture')}>
                    <Translate contentKey="akountsApp.bridgeTransaction.isFuture">Is Future</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('isDeleted')}>
                    <Translate contentKey="akountsApp.bridgeTransaction.isDeleted">Is Deleted</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('addedDate')}>
                    <Translate contentKey="akountsApp.bridgeTransaction.addedDate">Added Date</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedAt')}>
                    <Translate contentKey="akountsApp.bridgeTransaction.updatedAt">Updated At</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('checked')}>
                    <Translate contentKey="akountsApp.bridgeTransaction.checked">Checked</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {bridgeTransactionList.map((bridgeTransaction, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`${match.url}/${bridgeTransaction.id}`} color="link" size="sm">
                        {bridgeTransaction.id}
                      </Button>
                    </td>
                    <td>{bridgeTransaction.transactionId}</td>
                    <td>{bridgeTransaction.transactionType}</td>
                    <td>{bridgeTransaction.accountId}</td>
                    <td>{bridgeTransaction.amount}</td>
                    <td>
                      <Translate contentKey={`akountsApp.Currency.${bridgeTransaction.isoCurrencyCode}`} />
                    </td>
                    <td>
                      {bridgeTransaction.transactionDate ? (
                        <TextFormat type="date" value={bridgeTransaction.transactionDate} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{bridgeTransaction.description}</td>
                    <td>{bridgeTransaction.isFuture ? 'true' : 'false'}</td>
                    <td>{bridgeTransaction.isDeleted ? 'true' : 'false'}</td>
                    <td>
                      {bridgeTransaction.addedDate ? (
                        <TextFormat type="date" value={bridgeTransaction.addedDate} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {bridgeTransaction.updatedAt ? (
                        <TextFormat type="date" value={bridgeTransaction.updatedAt} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{bridgeTransaction.checked ? 'true' : 'false'}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${bridgeTransaction.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`${match.url}/${bridgeTransaction.id}/edit`}
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
                          to={`${match.url}/${bridgeTransaction.id}/delete`}
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
                <Translate contentKey="akountsApp.bridgeTransaction.home.notFound">No Bridge Transactions found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default BridgeTransaction;
