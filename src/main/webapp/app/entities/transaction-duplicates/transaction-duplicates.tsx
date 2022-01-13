import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities, reset } from './transaction-duplicates.reducer';
import { ITransactionDuplicates } from 'app/shared/model/transaction-duplicates.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TransactionDuplicates = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const transactionDuplicatesList = useAppSelector(state => state.transactionDuplicates.entities);
  const loading = useAppSelector(state => state.transactionDuplicates.loading);
  const totalItems = useAppSelector(state => state.transactionDuplicates.totalItems);
  const links = useAppSelector(state => state.transactionDuplicates.links);
  const entity = useAppSelector(state => state.transactionDuplicates.entity);
  const updateSuccess = useAppSelector(state => state.transactionDuplicates.updateSuccess);

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
      <h2 id="transaction-duplicates-heading" data-cy="TransactionDuplicatesHeading">
        <Translate contentKey="akountsApp.transactionDuplicates.home.title">Transaction Duplicates</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="akountsApp.transactionDuplicates.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="akountsApp.transactionDuplicates.home.createLabel">Create new Transaction Duplicates</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={transactionDuplicatesList ? transactionDuplicatesList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {transactionDuplicatesList && transactionDuplicatesList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="akountsApp.transactionDuplicates.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('isduplicate')}>
                    <Translate contentKey="akountsApp.transactionDuplicates.isduplicate">Isduplicate</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('dateAdd')}>
                    <Translate contentKey="akountsApp.transactionDuplicates.dateAdd">Date Add</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('action')}>
                    <Translate contentKey="akountsApp.transactionDuplicates.action">Action</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('checked')}>
                    <Translate contentKey="akountsApp.transactionDuplicates.checked">Checked</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="akountsApp.transactionDuplicates.parentTransactionId">Parent Transaction Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="akountsApp.transactionDuplicates.childTransactionId">Child Transaction Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {transactionDuplicatesList.map((transactionDuplicates, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`${match.url}/${transactionDuplicates.id}`} color="link" size="sm">
                        {transactionDuplicates.id}
                      </Button>
                    </td>
                    <td>{transactionDuplicates.isduplicate ? 'true' : 'false'}</td>
                    <td>
                      {transactionDuplicates.dateAdd ? (
                        <TextFormat type="date" value={transactionDuplicates.dateAdd} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{transactionDuplicates.action}</td>
                    <td>{transactionDuplicates.checked ? 'true' : 'false'}</td>
                    <td>
                      {transactionDuplicates.parentTransactionId ? (
                        <Link to={`bank-transaction/${transactionDuplicates.parentTransactionId.id}`}>
                          {transactionDuplicates.parentTransactionId.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {transactionDuplicates.childTransactionId ? (
                        <Link to={`bank-transaction/${transactionDuplicates.childTransactionId.id}`}>
                          {transactionDuplicates.childTransactionId.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`${match.url}/${transactionDuplicates.id}`}
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
                          to={`${match.url}/${transactionDuplicates.id}/edit`}
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
                          to={`${match.url}/${transactionDuplicates.id}/delete`}
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
                <Translate contentKey="akountsApp.transactionDuplicates.home.notFound">No Transaction Duplicates found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default TransactionDuplicates;
