import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IBankTransaction } from 'app/shared/model/bank-transaction.model';
import { getEntities as getBankTransactions } from 'app/entities/bank-transaction/bank-transaction.reducer';
import { getEntity, updateEntity, createEntity, reset } from './transaction-duplicates.reducer';
import { ITransactionDuplicates } from 'app/shared/model/transaction-duplicates.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TransactionDuplicatesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const bankTransactions = useAppSelector(state => state.bankTransaction.entities);
  const transactionDuplicatesEntity = useAppSelector(state => state.transactionDuplicates.entity);
  const loading = useAppSelector(state => state.transactionDuplicates.loading);
  const updating = useAppSelector(state => state.transactionDuplicates.updating);
  const updateSuccess = useAppSelector(state => state.transactionDuplicates.updateSuccess);
  const handleClose = () => {
    props.history.push('/transaction-duplicates');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getBankTransactions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dateAdd = convertDateTimeToServer(values.dateAdd);

    const entity = {
      ...transactionDuplicatesEntity,
      ...values,
      parentTransactionId: bankTransactions.find(it => it.id.toString() === values.parentTransactionId.toString()),
      childTransactionId: bankTransactions.find(it => it.id.toString() === values.childTransactionId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          dateAdd: displayDefaultDateTime(),
        }
      : {
          ...transactionDuplicatesEntity,
          dateAdd: convertDateTimeFromServer(transactionDuplicatesEntity.dateAdd),
          parentTransactionId: transactionDuplicatesEntity?.parentTransactionId?.id,
          childTransactionId: transactionDuplicatesEntity?.childTransactionId?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.transactionDuplicates.home.createOrEditLabel" data-cy="TransactionDuplicatesCreateUpdateHeading">
            <Translate contentKey="akountsApp.transactionDuplicates.home.createOrEditLabel">
              Create or edit a TransactionDuplicates
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="transaction-duplicates-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('akountsApp.transactionDuplicates.isduplicate')}
                id="transaction-duplicates-isduplicate"
                name="isduplicate"
                data-cy="isduplicate"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('akountsApp.transactionDuplicates.dateAdd')}
                id="transaction-duplicates-dateAdd"
                name="dateAdd"
                data-cy="dateAdd"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('akountsApp.transactionDuplicates.action')}
                id="transaction-duplicates-action"
                name="action"
                data-cy="action"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.transactionDuplicates.checked')}
                id="transaction-duplicates-checked"
                name="checked"
                data-cy="checked"
                check
                type="checkbox"
              />
              <ValidatedField
                id="transaction-duplicates-parentTransactionId"
                name="parentTransactionId"
                data-cy="parentTransactionId"
                label={translate('akountsApp.transactionDuplicates.parentTransactionId')}
                type="select"
              >
                <option value="" key="0" />
                {bankTransactions
                  ? bankTransactions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="transaction-duplicates-childTransactionId"
                name="childTransactionId"
                data-cy="childTransactionId"
                label={translate('akountsApp.transactionDuplicates.childTransactionId')}
                type="select"
              >
                <option value="" key="0" />
                {bankTransactions
                  ? bankTransactions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/transaction-duplicates" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TransactionDuplicatesUpdate;
