import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities, reset } from './bank-transaction.reducer';
import { IBankTransaction } from 'app/shared/model/bank-transaction.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankTransaction = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const bankTransactionList = useAppSelector(state => state.bankTransaction.entities);
  const loading = useAppSelector(state => state.bankTransaction.loading);
  const totalItems = useAppSelector(state => state.bankTransaction.totalItems);
  const links = useAppSelector(state => state.bankTransaction.links);
  const entity = useAppSelector(state => state.bankTransaction.entity);
  const updateSuccess = useAppSelector(state => state.bankTransaction.updateSuccess);

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
        <InfiniteScroll
          dataLength={bankTransactionList ? bankTransactionList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {bankTransactionList && bankTransactionList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="akountsApp.bankTransaction.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('transactionId')}>
                    <Translate contentKey="akountsApp.bankTransaction.transactionId">Transaction Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('transactionDate')}>
                    <Translate contentKey="akountsApp.bankTransaction.transactionDate">Transaction Date</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('description')}>
                    <Translate contentKey="akountsApp.bankTransaction.description">Description</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('localAmount')}>
                    <Translate contentKey="akountsApp.bankTransaction.localAmount">Local Amount</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('localCurrency')}>
                    <Translate contentKey="akountsApp.bankTransaction.localCurrency">Local Currency</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('amount')}>
                    <Translate contentKey="akountsApp.bankTransaction.amount">Amount</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('currency')}>
                    <Translate contentKey="akountsApp.bankTransaction.currency">Currency</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('note')}>
                    <Translate contentKey="akountsApp.bankTransaction.note">Note</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('year')}>
                    <Translate contentKey="akountsApp.bankTransaction.year">Year</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('month')}>
                    <Translate contentKey="akountsApp.bankTransaction.month">Month</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('week')}>
                    <Translate contentKey="akountsApp.bankTransaction.week">Week</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('categorizedDate')}>
                    <Translate contentKey="akountsApp.bankTransaction.categorizedDate">Categorized Date</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('addDate')}>
                    <Translate contentKey="akountsApp.bankTransaction.addDate">Add Date</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('checked')}>
                    <Translate contentKey="akountsApp.bankTransaction.checked">Checked</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('rebasedDate')}>
                    <Translate contentKey="akountsApp.bankTransaction.rebasedDate">Rebased Date</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('deleted')}>
                    <Translate contentKey="akountsApp.bankTransaction.deleted">Deleted</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="akountsApp.bankTransaction.catId">Cat Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="akountsApp.bankTransaction.accountId">Account Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="akountsApp.bankTransaction.category">Category</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="akountsApp.bankTransaction.account">Account</Translate> <FontAwesomeIcon icon="sort" />
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
                        <TextFormat type="date" value={bankTransaction.transactionDate} format={APP_DATE_FORMAT} />
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
                    <td>
                      {bankTransaction.catId ? (
                        <Link to={`bank-category/${bankTransaction.catId.id}`}>{bankTransaction.catId.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {bankTransaction.accountId ? (
                        <Link to={`bank-account/${bankTransaction.accountId.id}`}>{bankTransaction.accountId.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {bankTransaction.category ? (
                        <Link to={`bank-category/${bankTransaction.category.id}`}>{bankTransaction.category.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {bankTransaction.account ? (
                        <Link to={`bank-account/${bankTransaction.account.id}`}>{bankTransaction.account.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
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
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default BankTransaction;
