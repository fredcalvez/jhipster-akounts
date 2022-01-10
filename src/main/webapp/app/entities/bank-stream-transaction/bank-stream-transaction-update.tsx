import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IBankTransaction } from 'app/shared/model/bank-transaction.model';
import { getEntities as getBankTransactions } from 'app/entities/bank-transaction/bank-transaction.reducer';
import { IBankStream } from 'app/shared/model/bank-stream.model';
import { getEntities as getBankStreams } from 'app/entities/bank-stream/bank-stream.reducer';
import { getEntity, updateEntity, createEntity, reset } from './bank-stream-transaction.reducer';
import { IBankStreamTransaction } from 'app/shared/model/bank-stream-transaction.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankStreamTransactionUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const bankTransactions = useAppSelector(state => state.bankTransaction.entities);
  const bankStreams = useAppSelector(state => state.bankStream.entities);
  const bankStreamTransactionEntity = useAppSelector(state => state.bankStreamTransaction.entity);
  const loading = useAppSelector(state => state.bankStreamTransaction.loading);
  const updating = useAppSelector(state => state.bankStreamTransaction.updating);
  const updateSuccess = useAppSelector(state => state.bankStreamTransaction.updateSuccess);
  const handleClose = () => {
    props.history.push('/bank-stream-transaction');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getBankTransactions({}));
    dispatch(getBankStreams({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...bankStreamTransactionEntity,
      ...values,
      transactionId: bankTransactions.find(it => it.id.toString() === values.transactionId.toString()),
      streamId: bankStreams.find(it => it.id.toString() === values.streamId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...bankStreamTransactionEntity,
          transactionId: bankStreamTransactionEntity?.transactionId?.id,
          streamId: bankStreamTransactionEntity?.streamId?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.bankStreamTransaction.home.createOrEditLabel" data-cy="BankStreamTransactionCreateUpdateHeading">
            <Translate contentKey="akountsApp.bankStreamTransaction.home.createOrEditLabel">
              Create or edit a BankStreamTransaction
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
                  id="bank-stream-transaction-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                id="bank-stream-transaction-transactionId"
                name="transactionId"
                data-cy="transactionId"
                label={translate('akountsApp.bankStreamTransaction.transactionId')}
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
                id="bank-stream-transaction-streamId"
                name="streamId"
                data-cy="streamId"
                label={translate('akountsApp.bankStreamTransaction.streamId')}
                type="select"
              >
                <option value="" key="0" />
                {bankStreams
                  ? bankStreams.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bank-stream-transaction" replace color="info">
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

export default BankStreamTransactionUpdate;
